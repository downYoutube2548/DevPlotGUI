package com.downyoutube.devplotgui.devplotgui.GuiPage;

public class PlayerPlotPage {
    public static Boolean isPageValid(int n, int page, int spaces) {
        if (page <= 0) {
            return false;
        }

        int upperBound = page * spaces;
        int lowerBound = upperBound - spaces;

        return n > lowerBound;
    }
}
