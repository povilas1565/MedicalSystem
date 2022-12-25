package controller;

import dto.DiagnosisDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.Diagnosis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.DiagnosisService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "api/diagnosis")
@CrossOrigin
@Api
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @GetMapping(value = "/getAllDiagnosis")
    @ApiOperation("Получение всех диагнозов")
    public ResponseEntity<List<DiagnosisDTO>> getDiagnosis() {
        List<Diagnosis> diagnosis = diagnosisService.findAll();
        List<DiagnosisDTO> diagnosisDTO = new ArrayList<DiagnosisDTO>();
        if (diagnosis == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        for (Diagnosis d : diagnosis) {
            DiagnosisDTO dto = new DiagnosisDTO(d);
            diagnosisDTO.add(dto);
        }

        return new ResponseEntity<>(diagnosisDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/addDiagnosis", consumes = "application/json")
    @ApiOperation("Добавление диагнозов")
    public ResponseEntity<Void> addDiagnosis(@RequestBody DiagnosisDTO dto) {
        Diagnosis d = diagnosisService.findByCode(dto.getCode());

        if (d == null) {
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setName(dto.getName());
            diagnosis.setCode(dto.getCode());
            diagnosis.setTag(dto.getTag());

            diagnosisService.save(diagnosis);
        } else {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/updateDiagnosis/{code}")
    @ApiOperation("бновление(изменение) диагнозов согласно их кодам")
    public ResponseEntity<Void> updateDiagnosis(@RequestBody DiagnosisDTO dto, @PathVariable("code") String code) {
        Diagnosis diagnosis = diagnosisService.findByCode(code);

        if (diagnosis != null) {
            diagnosis.setName(dto.getName());
            diagnosis.setTag(dto.getTag());
            diagnosisService.save(diagnosis);
        } else {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteDiagnosis/{code}")
    @ApiOperation("Удаление диагнозов согласно их кодам")
    public ResponseEntity<Void> deleteDiagnosis(@PathVariable("code") String code) {
        Diagnosis diagnosis = diagnosisService.findByCode(code);

        if (diagnosis == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            diagnosisService.delete(diagnosis);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}


