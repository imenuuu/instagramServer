package com.example.demo.src.message;

import com.example.demo.src.message.model.DmRoom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {
    private Map<String, DmRoom> dmRooms;

    @PostConstruct
    private void init(){
        dmRooms=new LinkedHashMap<>();
    }

    public List<DmRoom> findAllRoom(){
        List<DmRoom> result=new ArrayList<>(dmRooms.values());
        Collections.reverse(result);
        return result;
    }

    public DmRoom findById(String roomId){
        return dmRooms.get(roomId);
    }


}
