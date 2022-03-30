// Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
// Available From: http://www.reflections.ath.cx
// Decompiler options: packimports(3) 

package com.cgtsi.investmentfund;

import java.io.Serializable;

public class PlanInvestment
    implements Serializable
{

    public PlanInvestment()
    {
    }

    public double getAvailableBalance()
    {
        return availableBalance;
    }

    public double getDayExpenses()
    {
        return dayExpenses;
    }

    public double getMonthExpenses()
    {
        return monthExpenses;
    }

    public void setAvailableBalance(double d)
    {
        availableBalance = d;
    }

    public void setDayExpenses(double d)
    {
        dayExpenses = d;
    }

    public void setMonthExpenses(double d)
    {
        monthExpenses = d;
    }

    private double availableBalance;
    private double monthExpenses;
    private double dayExpenses;
}
