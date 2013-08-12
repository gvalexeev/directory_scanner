/*
* $Id
*
* (C) Copyright 1997 i-Teco, CJSK. All Rights reserved.
* i-Teco PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*
* Эксклюзивные права 1997 i-Teco, ЗАО.
* Данные исходные коды не могут использоваться и быть изменены
* без официального разрешения компании i-Teco.          
*/
package command.params;

/**
 * $Id
 * <p>Title: </p>
 * <p>Description: </p>
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
