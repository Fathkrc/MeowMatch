package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.enums.Gender;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class CatServiceTest {

    @Test
    void createsConversationIfNoneExists() {
//        var cats = mock(CatRepository.class);
//        var convos = mock(ConversationRepository.class);
//
//        var me = new Cat("me", "Luna", 2, Gender.FEMALE, "Helsinki", "", "Siamese", "bio");
//        var other = new Cat("other", "Milo", 3, Gender.MALE, "Helsinki", "", "Maine Coon", "bio");
//
//        when(cats.findById("me")).thenReturn(Optional.of(me));
//        when(cats.findAll()).thenReturn(List.of(me, other));
//        when(convos.findByParticipants("me", "other")).thenReturn(Optional.empty());
//
//        var service = new MatchService(cats, convos, new Random(0), catService); // inject Random for determinism
//
//        Conversation created = service.matchWithRandomCat("me", Optional.empty());
//
//        var captor = ArgumentCaptor.forClass(Conversation.class);
//        verify(convos).save(captor.capture());
//        Conversation saved = captor.getValue();
//
//        assertThat(saved.getParticipants()).containsExactlyInAnyOrder("me", "other");
//        assertThat(created.getParticipants()).containsExactlyInAnyOrder("me", "other");
//    }
//
//    @Test
//    void returnsExistingConversationIfExists() {
//        var cats = mock(CatRepository.class);
//        var convos = mock(ConversationRepository.class);
//
//        var me = new Cat("me", "Luna", 2, Gender.FEMALE, "Helsinki", "", "Siamese", "bio");
//        var other = new Cat("other", "Milo", 3, Gender.MALE, "Helsinki", "", "Maine Coon", "bio");
//        var existing = new Conversation("c1", List.of("me","other"), List.of());
//
//        when(cats.findById("me")).thenReturn(Optional.of(me));
//        when(cats.findAll()).thenReturn(List.of(me, other));
//        when(convos.findByParticipants("me", "other")).thenReturn(Optional.of(existing));
//
//        var service = new MatchService(cats, convos, new Random(0), catService);
//
//        var out = service.matchWithRandomCat("me", Optional.empty());
//        assertThat(out.getId()).isEqualTo("c1");
//        verify(convos, never()).save(any());
    }
}
