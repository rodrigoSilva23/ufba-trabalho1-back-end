package com.backendufbaendereco.demo.DTO;

import com.backendufbaendereco.demo.entities.andress.State;

public record StateResponse(
         Long id,
         String name,
         String abbreviation) {

   public static StateResponse fromState(State state){
       return new StateResponse(state.getId(), state.getName(), state.getAbbreviation());
   }
}
