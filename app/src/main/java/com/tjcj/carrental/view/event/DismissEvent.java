package com.tjcj.carrental.view.event;

import android.support.annotation.NonNull;

import com.tjcj.carrental.view.Card;


public class DismissEvent {
    private final Card mCard;

    public DismissEvent(@NonNull final Card card) {
        mCard = card;
    }

    public Card getCard() {
        return mCard;
    }
}
