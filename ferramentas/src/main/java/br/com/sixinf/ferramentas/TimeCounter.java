/**
 * 
 */
package br.com.sixinf.ferramentas;

import org.apache.log4j.Logger;

/**
 * @author maicon
 *
 */
public class TimeCounter implements Runnable {

    private int timeLimitMilis;

    private int timeElapsed;

    private Runnable runnable;

    private boolean ative;
    

    public TimeCounter(int timeLimitMilis, Runnable runnable) {
        this.timeLimitMilis = timeLimitMilis;
        this.timeElapsed = 0;
        this.runnable = runnable;
        this.ative = true;
    }

    public synchronized int getTimeElapsed() {
        return timeElapsed;
    }

    public synchronized void setTimeElapsed(int timeElapsed){
        this.timeElapsed = timeElapsed;
    }

    public synchronized void reset(){
        this.timeElapsed = 0;
    }

    public int getTimeLimitMilis() {
        return timeLimitMilis;
    }

    public void setTimeLimitMilis(int timeLimitMilis) {
        this.timeLimitMilis = timeLimitMilis;
    }

    public synchronized boolean isAtive() {
        return ative;
    }

    public synchronized void setAtive(boolean ative) {
        this.ative = ative;
    }
    
    public void start(String name) {
    	new Thread(this, name).start();
    }

    @Override
    public void run() {
        while (isAtive()){
            if ( ((getTimeElapsed()) * 1000) > getTimeLimitMilis() ) {
                runnable.run();
                ative = false;
                continue;
            }
            
            setTimeElapsed(getTimeElapsed() + 1);
            
            try {
                Thread.sleep(1000); //  a cada 1 segundo
                //log.debug("Contou 1 segundo");
                //log.debug("TimeElapsed: " + timeElapsed);
            } catch (InterruptedException ex) {
                Logger.getLogger(getClass()).error("Erro no Thread.sleep do Timer", ex);
            }
        }
    }
    

}
