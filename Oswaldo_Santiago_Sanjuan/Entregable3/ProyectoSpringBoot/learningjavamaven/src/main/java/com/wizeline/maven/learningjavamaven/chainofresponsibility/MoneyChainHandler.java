package com.wizeline.maven.learningjavamaven.chainofresponsibility;

public abstract class MoneyChainHandler {
    MoneyChainHandler nextHandler = null;
    Integer noteDenomination = 0;

    public MoneyChainHandler setNextHandler (MoneyChainHandler nextHandler) {
        this.nextHandler = nextHandler;
        return this.nextHandler;
    }

    public void handler (int dollarBill) {
        int notes = dollarBill / noteDenomination;
        int remainingAmount = dollarBill % noteDenomination;

        if (notes > 0) {
            System.out.printf("dispatched %d X %d = %d handled by %s \n",
                    noteDenomination, notes, (noteDenomination * notes), this.getClass().getSimpleName());
        }

        if (nextHandler != null && remainingAmount > 0) {
            nextHandler.handler(remainingAmount);
        }
    }

}