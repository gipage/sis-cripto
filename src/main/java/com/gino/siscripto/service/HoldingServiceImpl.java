package com.gino.siscripto.service;

import com.gino.siscripto.exceptions.*;
import com.gino.siscripto.model.entity.Currency;
import com.gino.siscripto.model.entity.Holding;
import com.gino.siscripto.model.entity.User;
import com.gino.siscripto.model.entity.Wallet;
import com.gino.siscripto.model.key.HoldingKey;
import com.gino.siscripto.repository.IHoldingDAO;
import com.gino.siscripto.repository.IWalletDAO;
import com.gino.siscripto.service.interfaces.ICurrencyService;
import com.gino.siscripto.service.interfaces.IHoldingService;
import com.gino.siscripto.service.interfaces.IWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class HoldingServiceImpl implements IHoldingService {
    @Autowired
    private IHoldingDAO iHoldingDAO;
    @Autowired
    private IWalletService walletService;
    @Autowired
    private ICurrencyService currencyService;

    @Override
    public Holding createHolding(Holding holding) throws ApiException {
        //verificamos que exista la wallet en la bd
        if (!walletService.walletExist(holding.getId().getId_wallet())) {
            throw new WalletDoesNotExist(holding.getId().getId_wallet());
        }
        //verficamos que exista la currency en la bd
        if (!currencyService.currencyExist(holding.getId().getTicker_currency())) {
            throw new CurrencyDoesNotExist(holding.getId().getTicker_currency());
        }
        /*Como ticker es unique, al insertar una entidad que ya existe en la tabla holding
        es decir, misma billetera, mismo ticker, distinto amount Spring Data JPA
        lo actualizará, hará match con la clave de holding
        * */
        //Actualizamos el saldo de la wallet
        BigDecimal price = currencyService.getPrice(holding.getId().getTicker_currency());
        BigDecimal balance = holding.getAmount().multiply(price);//valor en ars
        //actualiza el saldo
        walletService.UpdateBalance(holding.getId().getId_wallet(), balance);

        if (checkHolding(holding.getId())) { //ya tiene criptos que quiere depositar
            return updateHolding(holding, holding.getId(), 1);
        } else { //no tiene esa cripto --> la crea
            return iHoldingDAO.save(holding);
        }

    }

    @Override
    public Holding deleteHolding(HoldingKey key) throws ApiException {
        //no quita el saldo
        Optional<Holding> holding = iHoldingDAO.findById(key);
        if (holding.isPresent()) {
            iHoldingDAO.delete(holding.get());
            return holding.get();
        }
        throw new HoldingDoesNotExist(new Holding());
    }

    @Override
    public Holding updateHolding(Holding holdingRequest, HoldingKey key, int flag) throws ApiException {
        Optional<Holding> holding = iHoldingDAO.findById(key);
        BigDecimal amountFinal;
        if (holding.isPresent()) {
            if (flag == 1) //recibió y ya tenia
                amountFinal = holding.get().getAmount().add(holdingRequest.getAmount());
            else //intercambio y descontó
                amountFinal = holding.get().getAmount().subtract(holdingRequest.getAmount());

            holding.get().setId(holdingRequest.getId());
            holding.get().setAmount(amountFinal);
            return iHoldingDAO.save(holding.get());
        }
        throw new HoldingDoesNotExist(holdingRequest);
    }

    @Override
    public Boolean checkHoldingAmount(Holding holding) throws ApiException {
        Optional<Holding> holdingOptional = iHoldingDAO.findById(holding.getId());
        if (holdingOptional.isPresent()) {
            int flag = holdingOptional.get().getAmount().compareTo(holding.getAmount());
            //la tenencia es mayor que lo que se quiere intercambiar
            return flag >= 0;
        }
        throw new HoldingDoesNotExist(holding); //o dar mas info
    }

    @Override
    public Boolean checkHolding(HoldingKey holdingKey) {
        Optional<Holding> holdingOptional = iHoldingDAO.findById(holdingKey);
        if (holdingOptional.isPresent()) {
            return true; //llamo al update
        }
        return false; //llamo al create

    }
}
