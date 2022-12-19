package fr.fonkio;

import fr.fonkio.listener.EventCommandAutoCompleteInteraction;
import fr.fonkio.listener.EventSlashCommandInteraction;
import fr.fonkio.utils.Configuration;
import fr.fonkio.utils.IdEnum;
import fr.fonkiomessage.StringsConst;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.IOException;
import java.util.*;

public class MvWildBot {

    public static Configuration CONFIGURATION;
    private static JDA jda;

    static {
        Configuration configuration = null;
        try {
            configuration = new Configuration("data.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        CONFIGURATION = configuration;
    }

    public static void main(String[] args) throws InterruptedException {
        Set<GatewayIntent> intents = new HashSet<>(EnumSet.allOf(GatewayIntent.class));
        jda = JDABuilder.create(CONFIGURATION.getToken(), intents).setAutoReconnect(true).build();
        CONFIGURATION.save();
        jda.addEventListener(new EventSlashCommandInteraction());
        jda.addEventListener(new EventCommandAutoCompleteInteraction());
        Activity act = new MvWildBotActivity();
        jda.getPresence().setActivity(act);
        jda.updateCommands().addCommands(
                Commands.slash(IdEnum.COMMAND_ADDDOC.getId(), StringsConst.COMMAND_ADDDOC_DESC)
                        .addOption(OptionType.STRING, IdEnum.ARG_ID.getId(), StringsConst.ARG_ID_DESC, true)
                        .addOption(OptionType.STRING, IdEnum.ARG_TITLE.getId(), StringsConst.ARG_TITLE_DESC, true)
                        .addOption(OptionType.STRING, IdEnum.ARG_LINK.getId(), StringsConst.ARG_LINK_DESC, true)
                        .addOption(OptionType.STRING, IdEnum.ARG_DESC.getId(), StringsConst.ARG_DESC_DESC, false),
                Commands.slash(IdEnum.COMMAND_REMOVEDOC.getId(), StringsConst.COMMAND_REMOVEDOC_DESC)
                        .addOption(OptionType.STRING, IdEnum.ARG_ID.getId(), StringsConst.ARG_ID_DESC, true, true),
                Commands.slash(IdEnum.COMMAND_DOC.getId(), StringsConst.COMMAND_DOC_DESC)
                        .addOption(OptionType.STRING, IdEnum.ARG_ID.getId(), StringsConst.ARG_ID_DESC, true, true)
        ).queue();
        System.out.println("Bot connecté");
        CONFIGURATION.save();
    }


}
