package com.github.dd_buntar.goods_warehouse.app.cli.cmds;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
public class MessageCommand implements Command {
    private final String name;
    @Setter
    private String msg;

    @Override
    public void execute(String[] args) {
        System.out.println(this.msg);
    }

    @Override
    public String getHelp() {
        return this.name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
