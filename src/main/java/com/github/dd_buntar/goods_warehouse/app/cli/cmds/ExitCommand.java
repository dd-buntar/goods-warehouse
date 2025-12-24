package com.github.dd_buntar.goods_warehouse.app.cli.cmds;

import com.github.dd_buntar.goods_warehouse.db.PostgresSQLManager;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExitCommand implements Command {
    public static final String NAME = "exit";
    @Override
    public void execute(String[] args) {
        try {
            // Try to close DB connection gracefully before exiting
            PostgresSQLManager.getInstance().closeConnection();
        } catch (Exception e) {
            throw new RuntimeException("Error while closing DB connection on exit: " + e.getMessage());
        }
        System.exit(0);
    }

    @Override
    public String getHelp() {
        return NAME;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
