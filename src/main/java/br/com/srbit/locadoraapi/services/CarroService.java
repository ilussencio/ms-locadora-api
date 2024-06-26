package br.com.srbit.locadoraapi.services;

import br.com.srbit.locadoraapi.dto.CarroRecordDTO;
import br.com.srbit.locadoraapi.exceptions.DataBaseException;
import br.com.srbit.locadoraapi.exceptions.NotFoundException;
import br.com.srbit.locadoraapi.models.AgenciaModel;
import br.com.srbit.locadoraapi.models.CarroModel;
import br.com.srbit.locadoraapi.repositories.CarroRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor

@Service
public class CarroService {
    private final CarroRepository repository;
    private final AgenciaService agenciaService;

    public List<CarroModel> findAll() {
        return repository.findAll();
    }

    public CarroModel findById(UUID id) throws Exception{
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Carro não entrado com o id: "+ id));
    }

    public CarroModel save(CarroRecordDTO carroRecordDTO) throws Exception {
        CarroModel carroModel = new CarroModel();
        BeanUtils.copyProperties(carroRecordDTO, carroModel);

        carroModel.setAgencia(agenciaService.findById(carroRecordDTO.agencia()));
        try {
            return repository.save(carroModel);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException("Violação de integridade do banco de dados");
        }
    }

    public CarroModel update(CarroRecordDTO carroRecordDTO, UUID id) throws Exception {
        var carroModel = repository.findById(id).orElseThrow(() -> new NotFoundException("Carro não entrado com o id: "+ id));
        BeanUtils.copyProperties(carroRecordDTO, carroModel);

        try {
            return repository.save(carroModel);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException("Violação de integridade do banco de dados");
        }
    }

    public void delete(UUID id) throws Exception {
        var carroModel = repository.findById(id).orElseThrow(() -> new NotFoundException("Carro não entrado com o id: "+ id));
        try {
            repository.delete(carroModel);
        }catch (DataIntegrityViolationException e){
            throw new DataBaseException("Violação de integridade do banco de dados");
        }
    }
}
