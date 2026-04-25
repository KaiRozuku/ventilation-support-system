//package com.ipze.controller;
//
//import com.ipze.dto1.response.MessageResponse;
//import com.ipze.mapper.AlertMapper;
//import com.ipze.mapper.TransformerMapper;
//import com.ipze.services.interfaces.DataAnalystService;
//import com.ipze.dto1.AlertDto;
//import com.ipze.dto1.TransformerDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/analyst")
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//
//@PreAuthorize("hasAuthority('DATA_ANALYST')")
//public class DataAnalystController {
//
//    private final DataAnalystService analystService;
//    private final TransformerMapper transformerMapper;
//    private final AlertMapper alertMapper;
//
//    @GetMapping("/send")
//    public MessageResponse sendReport(){
//        return new MessageResponse("");
//    }
//
//    @GetMapping("/transformer/export/{id}")
//    public ResponseEntity<TransformerDto> exportTransformer(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(
//                analystService.exportTransformer(id)
//                        .map(transformerMapper::toDto)
//                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
//        );
//    }
//
//    @GetMapping("/transformers/export/{from}/{to}")
//    public ResponseEntity<List<TransformerDto>> getTransformersRange(@PathVariable("from") Long from,
//                                                                     @PathVariable("to") Long to) {
//        return ResponseEntity.ok(
//                analystService.exportTransformersRange(from, to)
//                        .stream()
//                        .map(transformerMapper::toDto)
//                        .toList()
//        );
//    }
//
//    @GetMapping("/transformers/export/all")
//    public ResponseEntity<List<TransformerDto>> getAll() {
//        return ResponseEntity.ok(
//                analystService.exportAllTransformers()
//                        .stream()
//                        .map(transformerMapper::toDto)
//                        .toList()
//        );
//    }
//
//    @GetMapping("/alerts")
//    public ResponseEntity<List<AlertDto>> getAllAlerts() {
//        return ResponseEntity.ok(
//                analystService.getAllErrors()
//                        .stream()
//                        .map(alertMapper::toDto)
//                        .toList()
//        );
//    }
//
//    @GetMapping("/alerts/critical")
//    public ResponseEntity<List<AlertDto>> getCriticalAlerts() {
//        return ResponseEntity.ok(
//                analystService.getCriticalAlerts()
//                        .stream()
//                        .map(alertMapper::toDto)
//                        .toList()
//        );
//    }
//
//    @GetMapping("/logs/export/{id}")
//    public ResponseEntity<List<String>> getLogs(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(analystService.exportTransformerLogs(id));
//    }
//}