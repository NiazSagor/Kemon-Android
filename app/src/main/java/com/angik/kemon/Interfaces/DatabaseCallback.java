package com.angik.kemon.Interfaces;

import java.util.List;

public interface DatabaseCallback {

    void onCallback(long childrenCount);
    void onCallBackNames(List<String> tabNamesFromDatabase);

}
