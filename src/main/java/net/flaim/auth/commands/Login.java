package net.flaim.auth.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.flaim.auth.DataBase;
import org.bukkit.entity.Player;

public class Login implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        Player player = (Player) commandSourceStack.getSender();
        DataBase.users.getUserData(player.getUniqueId().toString(), (res) -> {
            if (res == null) {
                commandSourceStack.getSender().sendRichMessage("Вы не зарегистрированы!");
            } else {
                if (!res.equals(strings[0])) {
                    commandSourceStack.getSender().sendRichMessage("Вы ввели не правильный пароль!");
                } else {
                    commandSourceStack.getSender().sendRichMessage("Вы успешно вошли в аккаунт!");
                }
            }
        });
    }
}
