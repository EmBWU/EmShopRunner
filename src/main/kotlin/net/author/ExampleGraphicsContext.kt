package net.author

import net.botwithus.rs3.imgui.ImGui
import net.botwithus.rs3.imgui.NativeInteger
import net.botwithus.rs3.imgui.Vector2f
import net.botwithus.rs3.script.ScriptConsole
import net.botwithus.rs3.script.ScriptGraphicsContext

class ExampleGraphicsContext(
    private val script: ExampleScript,
    console: ScriptConsole
) : ScriptGraphicsContext (console) {

    override fun drawSettings() {
        super.drawSettings()
        ImGui.Begin("Emma's Shop Runner", 0)
        ImGui.SetWindowSize(250f, -1f)
        ImGui.Text("My scripts state is: " + script.botState)
        ImGui.Text("Current Shop Index: " + script.getCurrentShop)
        val currentItem = NativeInteger(script.getCurrentShop)
        if (ImGui.Combo("Select Shop", currentItem, *shops.map { it.debug }.toTypedArray())) {
            for (i in shops.indices) {
                if (currentItem.get() == i) {
                    script.getCurrentShop = i
                }
            }
        }
        if (ImGui.Button("Start")) {
            script.botState = ExampleScript.BotState.SKILLING;
        }
        ImGui.SameLine()
        if (ImGui.Button("Stop")) {
            script.botState = ExampleScript.BotState.IDLE
        }
        script.bankPreset.set(ImGui.InputInt("Bank preset", script.bankPreset.get()))
        script.doSomething.set(ImGui.Checkbox("Do something", script.doSomething.get()))
        ImGui.End()
    }

    override fun drawOverlay() {
        super.drawOverlay()
    }

}