package command.params;

/**
 * $Id
 * <p>Title: Параметры команды help</p>
 * <p>Description: каждая команда имеет decription и умеет печатать информацию о допустимых параметрах</p>
 * <p>Author: g.alexeev (g.alexeev@i-teco.ru)</p>
 * <p>Date: 12.08.13</p>
 *
 * @version 1.0
 */
public enum HelpParam {
    EXIT("exit", "Stops all running threads and exits the program."),
    SCAN("scan", "Starts scanner for specified folder with decimal wait interval.") {
        @Override
        public String getInfo() {
            StringBuilder builder = new StringBuilder(super.getInfo());
            builder.append("Available options: \n");

            for(ScanParam param : ScanParam.values()) {
                builder.append(param.description());
            }
            return builder.toString();
        }
    },
    SHOW("show", "Shows all running threads."),
    STOP("stop", "Stops selected thread.");

    private String command;
    private String description;

    private HelpParam(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getInfo() {
        StringBuilder builder = new StringBuilder("Command ").
                append(getCommand()).append("\n").
                append(getDescription()).append("\n");
        return builder.toString();
    }
}
