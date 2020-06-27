package com.kakaopay.scattering.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ReceiveHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Receiver receiver;

    public ReceiveHistory(Receiver receiver) {
        this.receiver = receiver;
    }

    public boolean isEqualReceiver(Receiver receiver) {
        return this.receiver.equals(receiver);
    }
}
