package btc;

import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.bitfinex.v1.BitfinexExchange;
import org.knowm.xchange.kraken.KrakenExchange;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import java.util.Arrays;
import java.util.Timer;

/**
 * btc
 * Copyright (c) 2017 Dennis Suchomsky <dennis.suchomsky@gmail.com>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class Main {

	public static void main(String[] args) throws Exception {

		Timer timer = new Timer();
		// Start in einer Sekunde dann Ablauf alle 60 Sekunden
		/*
		timer.schedule(new RateLogger(ExchangeFactory.INSTANCE.createExchange(BTCChinaExchange.class.getName())), 20000, 60000*5);
		Timer timer2 = new Timer();
		timer2.schedule(new RateLogger(ExchangeFactory.INSTANCE.createExchange(OkCoinExchange.class.getName())), 40000, 60000*5);
		Timer timer4 = new Timer();
		timer4.schedule(new RateLogger(ExchangeFactory.INSTANCE.createExchange(BTCEExchange.class.getName())), 80000, 60000*5);
		Timer timer5 = new Timer();
		timer5.schedule(new RateLogger(ExchangeFactory.INSTANCE.createExchange(CoinbaseExchange.class.getName())), 100000, 60000*5);
		Timer timer7 = new Timer();
		timer7.schedule(new RateLogger(ExchangeFactory.INSTANCE.createExchange(PoloniexExchange.class.getName())), 140000, 60000*5);
*/
		//LinkedList trainingData = DatabaseManager.getInstance().getTrainingData("bitfinex_btcusd");
		Timer timer6 = new Timer();
		timer6.schedule(new RateLogger(ExchangeFactory.INSTANCE.createExchange(BitfinexExchange.class.getName())), 120000, 60000*5);

		Timer timer3 = new Timer();
		timer3.schedule(new RateLogger(ExchangeFactory.INSTANCE.createExchange(KrakenExchange.class.getName())), 60000, 60000*5);

	}

	/**
	 * Prints network output for the each element from the specified training set.
	 * @param nnet neural network
	 * @param testSet data set used for testing
	 */
	public static void testNeuralNetwork(NeuralNetwork nnet, DataSet testSet) {

		for(DataSetRow dataRow : testSet.getRows()) {
			nnet.setInput(dataRow.getInput());
			nnet.calculate();
			double[ ] networkOutput = nnet.getOutput();
			System.out.print("Input: " + Arrays.toString(dataRow.getInput()) );
			System.out.println(" Output: " + Arrays.toString(networkOutput) );
		}

	}
}
