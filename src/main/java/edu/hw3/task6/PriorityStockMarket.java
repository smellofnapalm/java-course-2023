package edu.hw3.task6;

import java.util.Comparator;
import java.util.PriorityQueue;

public class PriorityStockMarket implements StockMarket{
    static private final Comparator<Stock> cmp = Comparator.comparing(Stock::getValue).reversed();
    private final PriorityQueue<Stock> priorityQueue = new PriorityQueue<>(cmp);
    @Override
    public void add(Stock stock) {
        priorityQueue.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        priorityQueue.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return priorityQueue.peek();
    }
}
