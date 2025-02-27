package net.flaim.auth.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.flaim.auth.DataBase;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Register implements BasicCommand {
    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] args) {
        if (args.length < 1) {
            commandSourceStack.getSender().sendRichMessage("<red>Использование: /reg <пароль>");
            return;
        }
        Player player = (Player) commandSourceStack.getSender();
        DataBase.users.getUserData(player.getUniqueId().toString(), (res) -> {
            if (res == null) {
                DataBase.users.writeData(player.getUniqueId(), args[0]);
                commandSourceStack.getSender().sendPlainMessage("Вы успешно зарегистрировались!");
            } else {
                commandSourceStack.getSender().sendPlainMessage("Вы и так зарегистрированы!");
            }
        });

    }

}
