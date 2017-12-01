package btc;

import database.DatabaseManager;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;

import java.io.IOException;
import java.util.TimerTask;

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
public class RateLogger extends TimerTask {
	private Exchange exchange;
	/**
	 * When an object implementing interface <code>Runnable</code> is used
	 * to create a thread, starting the thread causes the object's
	 * <code>run</code> method to be called in that separately executing
	 * thread.
	 * <p>
	 * The general contract of the method <code>run</code> is that it may
	 * take any action whatsoever.
	 *
	 * @see Thread#run()
	 */
	@Override
	public void run() {

		// Interested in the public market data feed (no authentication)
		MarketDataService marketDataService = exchange.getMarketDataService();

		// Get the latest ticker data showing BTC to USD
		try {
			Ticker ticker = marketDataService.getTicker(CurrencyPair.BTC_USD);
			Double[] data =  {ticker.getAsk().doubleValue(), ticker.getBid().doubleValue(), ticker.getHigh().doubleValue(), ticker.getLow().doubleValue()};
			DatabaseManager.getInstance().logRate("bitfinex_btcusd", data);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	RateLogger(Exchange exchange){
		this.exchange = exchange;
	}
}
