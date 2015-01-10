//<editor-fold defaultstate="collapsed" desc="Jibberish">
package centralebank;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
//</editor-fold>

/**
 * In this class you can find all properties and operations for CentralBank. //CHECK
 *
 * @organization: Moridrin
 * @author J.B.A.J. Berkvens
 * @date 2014/07/04
 */
public class CentralBank extends UnicastRemoteObject implements ICentralBank {

    //<editor-fold defaultstate="collapsed" desc="Declarations">
    private ArrayList<String> transactions;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public CentralBank() throws RemoteException {
        this.transactions = new ArrayList<>();
    }
    //</editor-fold>

    //<editor-fold desc="Operations">
    //<editor-fold defaultstate="collapsed" desc="getTransactions(bankName)">
    @Override
    public String getTransactions(String bankName) throws RemoteException {
        String sendBack = "";
        for (String s : this.transactions) {
            if ("SNS".equals(bankName)) {
                int SNScheck = Integer.parseInt(s.substring(0, 9));

                if (SNScheck >= 200000000) {
                    sendBack = sendBack + s;
                }

            }

            if ("ING".equals(bankName)) {
                int SNScheck = Integer.parseInt(s.substring(0, 9));

                if (SNScheck <= 200000000) {
                    sendBack = sendBack + s;
                }
            }
            sendBack = sendBack + s;
        }
        return sendBack;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="saveTransaction(transaction)">
    @Override
    public void saveTransaction(String transaction) throws RemoteException {
        boolean add = this.transactions.add(transaction);
        System.out.println(transaction);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="removeTransaction(transaction)">
    @Override
    public void removeTransaction(String transaction) throws RemoteException {
        for (int i=0; i< transactions.size();i++){
            if (transactions.get(i).equals(transaction)){
                transactions.remove(i);
            }
        }
    }
    //</editor-fold>
    //</editor-fold>
}
