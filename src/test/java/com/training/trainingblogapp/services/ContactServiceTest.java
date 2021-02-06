package com.training.trainingblogapp.services;

import com.training.trainingblogapp.domain.dtos.MessageDTO;
import com.training.trainingblogapp.domain.model.Message;
import com.training.trainingblogapp.exceptions.InvalidInputException;
import com.training.trainingblogapp.repositories.ContactRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class ContactServiceTest {

    private static final MessageDTO messageDTO1 = new MessageDTO(1L,"Cezary Żak", "Kontakt 1", "cezaryzak@gmail.com", "Wiadomość tekstowa 1", false);
    private static final MessageDTO messageDTO2 = new MessageDTO(2L,"Everett Santiago", "Kontakt 2", "evereststantiago@gmail.com", "Wiadomość tekstowa 2", true);
    private static final MessageDTO messageDTO3 = new MessageDTO(3L,"Damien Roy", "Kontakt 3", "damianroy@gmail.com", "Wiadomość tekstowa 3", false);


    @Mock
    public ContactRepository contactRepository;

    @Mock
    public MappingService mappingService;

    @InjectMocks
    private ContactService contactService;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addMessage() {
        //given
        Message message = new Message(messageDTO1.getId(),messageDTO1.getName(),messageDTO1.getSubject(),messageDTO1.getContactEmail(),messageDTO1.getText(),messageDTO1.isStatus());

        //when
        Mockito.when(mappingService.messageDtoToMessage(messageDTO1)).thenReturn(message);
        Mockito.when(mappingService.messageToDtoMessage(message)).thenReturn(messageDTO1);
        given(contactRepository.save(message)).willAnswer(invocation -> invocation.getArgument(0));
        contactService.addMessage(messageDTO1);

        //then
        assertThat(messageDTO1).isEqualTo(mappingService.messageToDtoMessage(message));
        verify(contactRepository).save(any(Message.class));

    }

    @Test
    void shouldReturnAllMessageDTO_findAll() {
        //given
        Message message1 = new Message(messageDTO1.getId(),messageDTO1.getName(),messageDTO1.getSubject(),messageDTO1.getContactEmail(),messageDTO1.getText(),messageDTO1.isStatus());
        Message message2 = new Message(messageDTO2.getId(),messageDTO2.getName(),messageDTO2.getSubject(),messageDTO2.getContactEmail(),messageDTO2.getText(),messageDTO2.isStatus());
        Message message3 = new Message(messageDTO3.getId(),messageDTO3.getName(),messageDTO3.getSubject(),messageDTO3.getContactEmail(),messageDTO3.getText(),messageDTO3.isStatus());

        //when
        Mockito.when(mappingService.messageToDtoMessage(message1)).thenReturn(messageDTO1);
        Mockito.when(mappingService.messageToDtoMessage(message2)).thenReturn(messageDTO2);
        Mockito.when(mappingService.messageToDtoMessage(message3)).thenReturn(messageDTO3);
        Mockito.when(contactRepository.findAll()).thenReturn(Lists.list(message1,message2,message3));

        //then
        List<MessageDTO> actual = contactService.findAll();
        assertThat(actual).containsExactlyInAnyOrder(messageDTO1,messageDTO2,messageDTO3);
    }

    @Test
    void shouldReturn1MessageDTO_findById() {
        //given
        Message message1 = new Message(messageDTO1.getId(),messageDTO1.getName(),messageDTO1.getSubject(),messageDTO1.getContactEmail(),messageDTO1.getText(),messageDTO1.isStatus());

        //when
        Mockito.when(mappingService.messageToDtoMessage(message1)).thenReturn(messageDTO1);
        Mockito.when(contactRepository.findById(1L)).thenReturn(Optional.of(message1));

        //then
        assertThat(messageDTO1).isEqualTo(contactService.findById(message1.getId()));
    }

    @Test
    void shouldThrowInputNotFoundException_findById() {
        //given
        long id = 2;
        Message message1 = new Message(messageDTO1.getId(),messageDTO1.getName(),messageDTO1.getSubject(),messageDTO1.getContactEmail(),messageDTO1.getText(),messageDTO1.isStatus());

        //when
        Mockito.when(mappingService.messageToDtoMessage(message1)).thenReturn(messageDTO1);
        Mockito.when(contactRepository.findById(id)).thenReturn(Optional.empty());

        //then
        Assertions.assertThrows(InvalidInputException.class, () -> contactService.findById(id));
    }


    @Test
    void update() {
        //given
        Message message = new Message(messageDTO1.getId(),messageDTO1.getName(),messageDTO1.getSubject(),messageDTO1.getContactEmail(),messageDTO1.getText(),messageDTO1.isStatus());
        MessageDTO messageDTO = new MessageDTO(messageDTO1.getId(),messageDTO1.getName(),messageDTO1.getSubject(),messageDTO1.getContactEmail(),messageDTO1.getText(),messageDTO1.isStatus());

        //when
        Mockito.when(mappingService.messageToDtoMessage(message)).thenReturn(messageDTO);
        Mockito.when(mappingService.messageDtoToMessage(messageDTO)).thenReturn(message);
        given(contactRepository.save(message)).willReturn(message);

        //then
        messageDTO.setStatus(true);
        Mockito.when(mappingService.messageDtoToMessage(messageDTO)).thenReturn(message);
        contactService.update(messageDTO);
        assertThat(messageDTO.isStatus()).isEqualTo(true);

    }
    @Test
    void delete() {
        long id = 1L;
        contactService.delete(id);
        verify(contactRepository, times(1)).deleteById(id);
    }
}