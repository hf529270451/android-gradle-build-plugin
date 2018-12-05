package com.czb.chezhubang.plugin.custom.build


class Utils {
    static void executeCmd(cmd) {
        def p = cmd.execute()
        println p.text
        println p.exitValue()
    }
}