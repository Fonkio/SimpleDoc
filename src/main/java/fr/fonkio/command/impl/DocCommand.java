package fr.fonkio.command.impl;

import fr.fonkio.MvWildBot;
import fr.fonkio.command.AbstractCommand;
import fr.fonkio.utils.Doc;
import fr.fonkio.utils.EmbedGenerator;
import fr.fonkio.utils.IdEnum;
import fr.fonkiomessage.StringsConst;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

public class DocCommand extends AbstractCommand {
    @Override
    public void run(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if(guild == null) {
            return;
        }
        OptionMapping idOptionMapping = event.getOption(IdEnum.ARG_ID.getId());
        if (idOptionMapping == null) {
            return;
        }
        String docId = idOptionMapping.getAsString();
        if (!MvWildBot.CONFIGURATION.containsGuildDoc(guild.getId(), docId)) {
            event.replyEmbeds(EmbedGenerator.generate(event.getUser(), StringsConst.COMMAND_ERROR_NOT_EXIST_TITLE, StringsConst.COMMAND_ERROR_NOT_EXIST_DESC)).setEphemeral(true).queue();
        } else {
            Doc docToShow = MvWildBot.CONFIGURATION.getGuildDoc(guild.getId(), docId);
            event.replyEmbeds(EmbedGenerator.generate(event.getUser(), docToShow.getTitle(), docToShow.getDesc())).addActionRow(Button.link(docToShow.getLink(), docToShow.getId().substring(0,1).toUpperCase() + docToShow.getId().substring(1))).queue();
        }
    }
}
