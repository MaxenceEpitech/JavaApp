package com.maxence.epitech.epicture;

import android.content.Context;
import android.view.View;

import java.util.List;

public interface Command {
    void execute(List<ListArgs> args);
}