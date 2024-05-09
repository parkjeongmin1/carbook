package com.carbook.controller;

import com.carbook.dto.ReserveDto;
import com.carbook.dto.ReserveHistDto;
import com.carbook.service.ReserveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReserveController {
    private final ReserveService reserveService;

    @PostMapping(value = "/reserve")
    public @ResponseBody ResponseEntity reserve(@RequestBody @Valid ReserveDto reserveDto,
                                              BindingResult bindingResult, Principal principal) {

        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();

            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long reserveId;

        try {
            reserveId = reserveService.reserve(reserveDto, email);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(reserveId, HttpStatus.OK);
    }

    @GetMapping(value = {"/reserves", "/reserves/{page}"})
    public String reserveHist(@PathVariable("page") Optional<Integer> page,
                            Principal principal, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);

        Page<ReserveHistDto> reserveHistDtoList =
                reserveService.getReserveList(principal.getName(), pageable);

        model.addAttribute("reserves", reserveHistDtoList);
        model.addAttribute("maxPage", 5);

        return "reserve/reserveHist";
    }

    @PatchMapping("/reserve/{reserveId}/cancel")
    public @ResponseBody ResponseEntity cancelReserve(@PathVariable("reserveId") Long reserveId,
                                                    Principal principal) {

        if(!reserveService.validateReserve(reserveId, principal.getName())) {
            return new ResponseEntity<String>("예약 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        reserveService.cancelReserve(reserveId);

        return new ResponseEntity<Long>(reserveId, HttpStatus.OK);
    }

    @DeleteMapping("/reserve/{reserveId}/delete")
    public @ResponseBody ResponseEntity deleteReserve(@PathVariable("reserveId") Long reserveId
            , Principal principal) {
        //1. 본인인증
        if(!reserveService.validateReserve(reserveId, principal.getName())) {
            return new ResponseEntity<String>("예약 삭제 권한이 없습니다.",
                    HttpStatus.FORBIDDEN);
        }

        //2.주문삭제
        reserveService.deleteReserve(reserveId);

        return new ResponseEntity<Long>(reserveId, HttpStatus.OK);
    }
}
