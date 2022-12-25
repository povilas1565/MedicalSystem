package controller;

import dto.DrugDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Drug;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.DrugService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "api/drug")
@CrossOrigin
@Api
public class DrugController {

    @Autowired
    private DrugService drugService;

    @GetMapping(value = "/getAllDrugs")
    @ApiOperation("Получение списка всех доступных лекарств")
    public ResponseEntity<List<DrugDTO>> getDrugs() {
        List<Drug> drugs = drugService.findAll();
        List<DrugDTO> drugsDTO = new ArrayList<DrugDTO>();
        if (drugs == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Drug d : drugs) {
            DrugDTO dto = new DrugDTO(d);
            drugsDTO.add(dto);
        }

        return new ResponseEntity<>(drugsDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/addDrug", consumes = "application/json")
    @ApiOperation("Добавление лекарств")
    public ResponseEntity<Void> addDrug(@RequestBody DrugDTO dto) {
        Drug d = drugService.findByCode(dto.getCode());

        if (d == null) {
            Drug drug = new Drug();
            drug.setName(dto.getName());
            drug.setCode(dto.getCode());

            drugService.save(drug);
        } else {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value ="/updateDrug/{code}")
    @ApiOperation("Обновление(изменение) лекарств согласно их кодам")
    public ResponseEntity<Void> updateDrug(@RequestBody DrugDTO dto, @PathVariable("code") String code)
    {
        Drug drug = drugService.findByCode(code);

        if(drug != null) {
            drug.setName(dto.getName());
            drugService.save(drug);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value ="/deleteDrug/{code}")
    @ApiOperation("Удаление лекарств согласно их кодам")
    public ResponseEntity<Void> deleteDrug(@PathVariable("code") String code)
    {

        Drug drug = drugService.findByCode(code);


        if(drug == null)
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            drugService.delete(drug);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
