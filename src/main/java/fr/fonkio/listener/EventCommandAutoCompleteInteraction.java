package fr.fonkio.listener;

import fr.fonkio.MvWildBot;
import fr.fonkio.utils.IdEnum;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EventCommandAutoCompleteInteraction extends ListenerAdapter {

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (event.getFocusedOption().getName().equals(IdEnum.ARG_ID.getId())) {
            Set<String> allOption = MvWildBot.CONFIGURATION.getGuildDocIdList(event.getGuild().getId());
            List<Command.Choice> filteredOption = allOption.stream()
                    .filter(option -> option.startsWith(event.getFocusedOption().getValue()))
                    .map(option -> new Command.Choice(option, option))
                    .collect(Collectors.toList());
            event.replyChoices(filteredOption).queue();
        }
    }
}
