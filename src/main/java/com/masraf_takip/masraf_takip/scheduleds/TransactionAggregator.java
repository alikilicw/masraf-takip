package com.masraf_takip.masraf_takip.scheduleds;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.masraf_takip.masraf_takip.model.AggregatedTransaction;
import com.masraf_takip.masraf_takip.model.RecordType;
import com.masraf_takip.masraf_takip.model.Transaction;
import com.masraf_takip.masraf_takip.model.User;
import com.masraf_takip.masraf_takip.service.AggregatedTransactionService;
import com.masraf_takip.masraf_takip.service.TransactionService;
import com.masraf_takip.masraf_takip.service.UserService;

@Component
public class TransactionAggregator {

    private TransactionService transactionService;
    private AggregatedTransactionService aggregatedTransactionService;
    private UserService userService;

    public TransactionAggregator(TransactionService transactionService, AggregatedTransactionService aggregatedTransactionService, UserService userService) {
        this.transactionService = transactionService;
        this.aggregatedTransactionService = aggregatedTransactionService;
        this.userService = userService;
    }

    @Scheduled(cron ="*/10 * * * * ?") // Each day "0 0 0 * * ?"
    public void aggregateDailyTransactions() {
        Date date = new Date();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        Date startDate = calendar.getTime();
        
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date endDate = calendar.getTime();

        List<User> users = userService.findAll();
        for(User user : users) {
            List<Transaction> transactions = transactionService.findByDateAndUser(user.getId(), startDate, endDate);

            Double sumOfTransactions = transactions.stream().mapToDouble(Transaction::getAmount).sum();
            
            AggregatedTransaction aggregatedTransaction = new AggregatedTransaction();
            aggregatedTransaction.setUserId(user.getId());
            aggregatedTransaction.setType(RecordType.DAILY);
            aggregatedTransaction.setSumOfTransactions(sumOfTransactions);
            aggregatedTransactionService.add(aggregatedTransaction);
            
            System.out.println(aggregatedTransaction.getSumOfTransactions());
        }


    }

    @Scheduled(cron = "0 0 0 * * MON") // Each week
    public void aggregateWeeklyTransactions() {
        
    }

    @Scheduled(cron = "0 0 0 1 * ?") // Each month
    public void aggregateMonthlyTransactions() {
        
    }
}