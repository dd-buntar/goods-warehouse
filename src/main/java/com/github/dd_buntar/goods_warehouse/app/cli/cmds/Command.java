package com.github.dd_buntar.goods_warehouse.app.cli.cmds;

public interface Command {
    void execute(String[] args);
    String getHelp();
    String getName();
}
