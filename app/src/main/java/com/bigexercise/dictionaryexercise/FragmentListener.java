package com.bigexercise.dictionaryexercise;

public interface FragmentListener {
    // Sẽ là nơi giao tiếp trung gian giữa fragment và main activity
    void OnItemClick(String value); // Thằng này sẽ làm việc với ListView. Khi ta click vào mỗi item của ListView

}
