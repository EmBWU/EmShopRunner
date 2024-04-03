package net.author

import net.botwithus.api.game.hud.inventories.Backpack
import net.botwithus.internal.scripts.ScriptDefinition
import net.botwithus.rs3.game.Area
import net.botwithus.rs3.game.Client
import net.botwithus.rs3.game.Coordinate
import net.botwithus.rs3.game.hud.interfaces.Interfaces
import net.botwithus.rs3.game.minimenu.MiniMenu
import net.botwithus.rs3.game.minimenu.actions.ComponentAction
import net.botwithus.rs3.game.movement.Movement
import net.botwithus.rs3.game.movement.NavPath
import net.botwithus.rs3.game.movement.TraverseEvent
import net.botwithus.rs3.game.queries.builders.characters.NpcQuery
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery
import net.botwithus.rs3.game.scene.entities.characters.npc.Npc
import net.botwithus.rs3.game.scene.entities.characters.player.Player
import net.botwithus.rs3.imgui.NativeBoolean
import net.botwithus.rs3.imgui.NativeInteger
import net.botwithus.rs3.script.Execution
import net.botwithus.rs3.script.LoopingScript
import net.botwithus.rs3.script.config.ScriptConfig

import java.util.*
import java.util.regex.Pattern

class ExampleScript(
    name: String,
    scriptConfig: ScriptConfig,
    scriptDefinition: ScriptDefinition
) : LoopingScript(name, scriptConfig, scriptDefinition) {

    val random: Random = Random()
    var botState: BotState = BotState.IDLE
    var bankPreset: NativeInteger = NativeInteger(1)
    var doSomething: NativeBoolean = NativeBoolean(false)
    var getCurrentShop = 0

    enum class BotState {
        IDLE,
        SKILLING,
        TALKING,
        BUYING,
        MOVING,
    }

    override fun initialize(): Boolean {
        super.initialize()
        // Set the script graphics context to our custom one
        this.sgc = ExampleGraphicsContext(this, console)
        println("My script loaded!")
        return true;
    }

    override fun onLoop() {
        val player = Client.getLocalPlayer();
        if (Client.getGameState() != Client.GameState.LOGGED_IN || player == null || botState == BotState.IDLE) {
            Execution.delay(random.nextLong(2500, 5500))
            return
        }
        useFeatherPack()
        when (botState) {
            BotState.SKILLING -> {
                Execution.delay(handleSkilling(player))
                return
            }

            BotState.TALKING -> {
                Execution.delay(handleTalking(player))
                return
            }
            BotState.BUYING -> {
                Execution.delay(handleBuying(player))
                return
            }
            BotState.MOVING -> {
                Execution.delay(handleMoveTo(player))
                return
            }
            else -> {
                println("Unexpected bot state, report to author!")
            }
        }
        Execution.delay(random.nextLong(2000, 4000))
        return
    }


    fun useFeatherPack() {
        var featherPack = InventoryItemQuery.newQuery().name("Feather pack").results().first()
        while (featherPack != null) {
            val use = Backpack.interact(featherPack.slot, "Open-All")
            if (use) {
                println("Used feather pack")
                Execution.delay(random.nextLong(1000, 2000))
            }
            featherPack = InventoryItemQuery.newQuery().name("Feather pack").results().first()
        }
    }
    private fun handleMoveTo(player: Player): Long {
        if (player.isMoving || player.animationId != -1)
            return random.nextLong(1000, 2000)
        val shop = shops[getCurrentShop]
        val corner1 = shop.position
        val corner2 = Coordinate(corner1.x + 1, corner1.y + 1, corner1.z)
        val area = Area.Rectangular(corner1, corner2)

        if (!area.contains(player)) {
            println("Moving to shop ${shop.debug}")
            val coordinate = NavPath.resolve(area.randomWalkableCoordinate)
            val result = Movement.traverse(coordinate)
            if (result == TraverseEvent.State.NO_PATH) {
                println("No path to shop")
            } else if (result == TraverseEvent.State.FINISHED) {
                println("Arrived at shop")
                botState = BotState.TALKING

            }
            return random.nextLong(1000, 3000)
        }

        return random.nextLong(1000, 3000)
    }

    private fun handleTalking(player: Player): Long {
        if (player.isMoving || player.animationId != -1)
            return random.nextLong(1000, 2000)
        val shop = shops[getCurrentShop]
        val npc: Npc? = NpcQuery.newQuery().name(shop.name).results().nearest()
        if (npc != null) {
            if (!Interfaces.isOpen(1265)) {
                if (npc.interact("Trade")) {
                    println("Talking to ${shop.name}")
                }
            } else {
                print("Buying items")
                botState = BotState.BUYING
            }
        }
        return random.nextLong(1000, 3000)
    }
    private fun handleBuying(player: Player): Long {
        if (player.isMoving || player.animationId != -1 || !Interfaces.isOpen(1265)) {
            return random.nextLong(1000, 2000)
        }
        val shop = shops[getCurrentShop]
        shop.thingsToBuy.forEach { itemName ->
            val pattern = Pattern.compile(itemName)
            val predicate = Shop.nameMatcher(pattern)
            if (Shop.contains(predicate) && Shop.getAmount(predicate) > 0) {
                Execution.delay(1000)
                println("Buying $itemName")
                Shop.buyAll(predicate)
                MiniMenu.interact(ComponentAction.COMPONENT.type, 1, -1, 82903210)

            }
        }
        if (true) {
            println("Closing shop and moving to next")
            moveToNextShop()
        }

        return random.nextLong(1000, 3000)
    }
    private fun moveToNextShop() {
        getCurrentShop++
        val nextShop = shops[getCurrentShop]
        println("Moving to next shop ${nextShop.debug}")
        botState = BotState.MOVING
    }

    private fun handleSkilling(player: Player): Long {
        if (player.isMoving || player.animationId != -1)
            return random.nextLong(1000, 2000)
            val shop = shops[getCurrentShop]
            val npc: Npc? = NpcQuery.newQuery().name(shop.name).results().nearest()
            if (npc != null) {
                botState = BotState.TALKING
            } else {
                botState = BotState.MOVING
            }
        return random.nextLong(1000, 3000)
    }
}