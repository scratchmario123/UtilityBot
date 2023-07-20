import com.eme22.anime.AnimeImageClient;
import com.eme22.anime.Endpoints;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImageGenerator extends ListenerAdapter {

    private String[] imgSources = {"WAIFU API","NEKO API","HMTAI API","KAWAII API"};

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("img")) {
            String option = event.getOption("sources").getAsString();
            if (Arrays.asList(imgSources).contains(option)) {
                event.replyEmbeds(getImg(option.split(" ")[0].toLowerCase()))
                        .addActionRow(StringSelectMenu.create("sources")
                                .addOption("WAIFU API", "waifu")
                                .addOption("NEKO API", "neko")
                                .addOption("HMTAI API", "hmtai")
                                .addOption("KAWAII API", "kawaii").build()).queue();
            } else {
                event.reply("Not a valid source").setEphemeral(true).queue();
            }
        }
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {
        if (event.getComponentId().equals("sources")) {
            event.replyEmbeds(getImg(event.getValues().get(0))).addActionRow(StringSelectMenu.create("sources")
                    .addOption("WAIFU API", "waifu")
                    .addOption("NEKO API", "neko")
                    .addOption("HMTAI API", "hmtai")
                    .addOption("KAWAII API", "kawaii").build()).queue();
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        if (event.getName().equals("img") && event.getFocusedOption().getName().equals("sources")) {
            List<Command.Choice> options = Stream.of(imgSources)
                    .filter(word -> word.startsWith(event.getFocusedOption().getValue().toUpperCase()))
                    .map(word -> new Command.Choice(word,word))
                    .collect(Collectors.toList());
            event.replyChoices(options).queue();
        }
    }

    public MessageEmbed getImg(String type) {
        AnimeImageClient client = new AnimeImageClient();
        try {
            switch (type) {
                case "waifu" -> {
                    return new EmbedBuilder().setAuthor("UtilityBot").setDescription("WAIFU API").setImage(client.getImage(Endpoints.WAIFU_SFW.WAIFU)).build();
                }
                case "neko" -> {
                    return new EmbedBuilder().setAuthor("UtilityBot").setDescription("NEKO API").setImage(client.getImage(Endpoints.NEKO.WAIFU)).build();
                }
                case "hmtai" -> {
                    return new EmbedBuilder().setAuthor("UtilityBot").setDescription("HMTAI API").setImage(client.getImage(Endpoints.HM_SFW.NEKO)).build();
                }
                case "kawaii" -> {
                    return new EmbedBuilder().setAuthor("UtilityBot").setDescription("KAWAII API").setImage(client.getImage(Endpoints.KAWAII_SFW.WAIFU)).build();
                }
                default -> {
                    return new EmbedBuilder().setAuthor("UtilityBot").setDescription("AN ERROR OCCURRED. PLEASE PING <@649240977785094156>.").build();
                }

            }
        } catch (Exception ignored) {
            return new EmbedBuilder().setAuthor("UtilityBot").setDescription("AN ERROR OCCURRED. PLEASE PING <@649240977785094156>.").build();
        }
    }
}

