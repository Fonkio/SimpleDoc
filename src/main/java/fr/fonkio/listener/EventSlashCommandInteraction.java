package fr.fonkio.listener;

import fr.fonkio.command.AbstractCommand;
import fr.fonkio.command.impl.AddDocCommand;
import fr.fonkio.command.impl.DocCommand;
import fr.fonkio.command.impl.RemoveDocCommand;
import fr.fonkio.utils.IdEnum;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class EventSlashCommandInteraction extends ListenerAdapter {

    AbstractCommand addDocCommand = new AddDocCommand();
    AbstractCommand removeDocCommand = new RemoveDocCommand();
    AbstractCommand docCommand = new DocCommand();

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        IdEnum id = IdEnum.parseString(event.getName());
        if (id == null) {
            return;
        }
        switch (id) {
            case COMMAND_ADDDOC:
                addDocCommand.run(event);
                break;
            case COMMAND_REMOVEDOC:
                removeDocCommand.run(event);
                break;
            case COMMAND_DOC:
                docCommand.run(event);
                break;
        }
    }
}
