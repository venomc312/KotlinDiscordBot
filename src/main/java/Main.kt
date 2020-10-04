import net.dv8tion.jda.core.AccountType
import net.dv8tion.jda.core.EmbedBuilder
import net.dv8tion.jda.core.JDABuilder
import net.dv8tion.jda.core.Permission
import net.dv8tion.jda.core.entities.Game
import net.dv8tion.jda.core.entities.Guild
import net.dv8tion.jda.core.entities.Invite
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent
import net.dv8tion.jda.core.hooks.ListenerAdapter
import java.awt.Color
import javax.security.auth.login.LoginException


class Main : ListenerAdapter() {

    override fun onPrivateMessageReceived(event: PrivateMessageReceivedEvent) {
        if (event.author.isBot) {
            return;
        } else {
            val eb = EmbedBuilder()
                    .setTitle("DM Commands currently are not supported!")
                    .setAuthor("Message Received!")
                    .setDescription("privateresp.sendMessage()")
                    .setColor(Color.GRAY)
                    .build()
            event.channel.sendMessage(eb).queue()
        }
    }

    override fun onGuildMessageReceived(event: GuildMessageReceivedEvent) {
        // Checks if message comes from bot
        if (event.author.isBot) {
            return;
        }

        // Ping
        if (event.message.contentRaw == "kotlin.ping") {
            val eb = EmbedBuilder()
                    .setTitle("Message Received!")
                    .setAuthor("Ping!")
                    .setDescription("resp.sendMessage()")
                    .setColor(Color.GRAY)
                    .build()
            event.channel.sendMessage(eb).queue()
        }

        // BotInfo
        if (event.message.contentRaw == "kotlin.info") {
            val builder = EmbedBuilder()
                    .setTitle("A bot made in kotlin with a console-like interface.")
                    .setDescription("bot.getInfo()")
                    .setAuthor("Author - VenomC312", null, "https://www.dropbox.com/s/l07qs9bqjca12kz/Venom%20Logo%20Still.png?dl=1")
                    .setColor(Color.orange)
                    .build()
            event.channel.sendMessage(builder).queue()
        }

        var words = event.getMessage().getContentRaw().split((" ").toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray();

        if (event.message.contentRaw.contains("kotlin.ban")) {

            var author = event.author

            if (!event.guild.getMember(event.author).hasPermission(Permission.BAN_MEMBERS)) {

                val eb = EmbedBuilder()
                        .setTitle("You don't have access to this command.")
                        .setAuthor("Ban")
                        .setDescription("bot.ban(user, reason)")
                        .setColor(Color.RED)
                        .setFooter("check description to see arguments.", null)
                        .build()
                event.channel.sendMessage(eb).queue()
                return;
            }

            if (words.size.equals(1)) {
                val eb = EmbedBuilder()
                        .setTitle("No arguments were provided.")
                        .setAuthor("Ban")
                        .setDescription("bot.ban(user, reason)")
                        .setColor(Color.RED)
                        .setFooter("check description to see arguments.", null)
                        .build()
                event.channel.sendMessage(eb).queue()
            }

            try {
                var banid = words[1].replace("<", "").replace("!", "").replace(">", "").replace("@", "");


                var a = words.drop(1)
                var b = a.drop(1)

                var reason = b.asSequence().joinToString().replace(",", "")

                System.out.println("Ban function executed, see below.")
                System.out.println("reason: "+ reason)
                System.out.println("banid" + banid);
                event.guild.controller.ban(banid, 7, reason).queue();
                val eb = EmbedBuilder()
                        .setTitle("Success!")
                        .setAuthor("Ban reason: " + reason)
                        .setDescription("bot.ban(user, reason)")
                        .setColor(Color.RED)
                        .build()
                event.channel.sendMessage(eb).queue()
            } catch (e: Exception) {
                val eb = EmbedBuilder()
                        .setTitle("Ban failed, do I have permission?")
                        .setAuthor("Ban")
                        .setDescription("bot.ban(user, reason)")
                        .setColor(Color.RED)
                        .setFooter("check description to see arguments.", null)
                        .build()
                event.channel.sendMessage(eb).queue()
            }
        }
    }

    companion object {
        @Throws(LoginException::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val builder = JDABuilder(AccountType.BOT)
            val token = "NzYyMDAzNjExNTY2ODY2NDcy.X3i1Kg.HJMnkJKRQIgUErq8-yVYN2CoKLI"
            builder.setToken(token)
            builder.setAutoReconnect(true)
            builder.setGame(Game.playing("Compiling Main.kt"))
            builder.addEventListener(Main())
            builder.buildAsync()
        }
    }
}
