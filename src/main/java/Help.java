import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {
    String[] commands = {"/help","/img","/ping","/weather"};

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isWebhookMessage()) {
            if (!event.getMember().getUser().isBot()) {
                String msg = event.getMessage().getContentRaw().replaceAll("\\s{2,}", " ").trim();
                String[] args = msg.split(" ");
                if (args[0].equalsIgnoreCase("!help")) {
                    event.getChannel().sendMessageEmbeds(embed()).queue();
                }
            }
        }
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("help")) {
            event.replyEmbeds(embed()).queue();
        }
    }

    public MessageEmbed embed() {
        StringBuilder cmds = new StringBuilder();
        for (String cmd : commands) {
            cmds.append(cmd).append("\n");
        }
        return new EmbedBuilder().setAuthor("UtilityBot")
                .addField("**Commands**",cmds.toString(),false)
                        .build();
    }
}
