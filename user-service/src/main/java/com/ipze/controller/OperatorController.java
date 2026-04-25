//package com.ipze.controller;
//
//import com.ipze.dto1.AlertDto;
//import com.ipze.dto1.TransformerDto;
//import com.ipze.mapper.AlertMapper;
//import com.ipze.mapper.TransformerMapper;
//import com.ipze.services.interfaces.OperatorService;
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
//@RequestMapping("/api/operator")
//@CrossOrigin(origins = "*")
//@PreAuthorize("hasAuthority('OPERATOR')")
//@RequiredArgsConstructor
//public class OperatorController {
//
//    private final OperatorService operatorService;
//    private final TransformerMapper transformerMapper;
//    private final AlertMapper alertMapper;
//
//    @GetMapping("/transformers")
//    public ResponseEntity<List<TransformerDto>> getAllTransformers() {
//        return ResponseEntity.ok(
//                operatorService.getAllTransformers()
//                        .stream()
//                        .map(transformerMapper::toDto)
//                        .toList()
//        );
//    }
//
//    @GetMapping("/transformers/{id}")
//    public ResponseEntity<TransformerDto> getTransformer(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(
//                operatorService.getTransformer(id)
//                        .map(transformerMapper::toDto)
//                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND))
//        );
//    }
//
//    @GetMapping("/transformers/{id}/alerts")
//    public ResponseEntity<List<AlertDto>> getAlerts(@PathVariable("id") Long id) {
//        return ResponseEntity.ok(
//                operatorService.getTransformerAlerts(id)
//                        .stream()
//                        .map(alertMapper::toDto)
//                        .toList()
//        );
//    }
//}