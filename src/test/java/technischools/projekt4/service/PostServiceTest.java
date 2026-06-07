package technischools.projekt4.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import technischools.projekt4.event.PostPublishedEvent;
import technischools.projekt4.exception.ResourceNotFoundException;
import technischools.projekt4.model.Post;
import technischools.projekt4.model.PostCategory;
import technischools.projekt4.repository.PostRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private PostService postService;

    private Post samplePost;

    @BeforeEach
    void setUp() {
        samplePost = new Post(
                1L,
                List.of(),
                false,
                LocalDateTime.of(2024, 1, 15, 10, 0),
                "Jan Kowalski",
                PostCategory.INFO,
                "Treść ogłoszenia testowego",
                "Tytuł testowy"
        );
    }

    // ---------------------------------------------------------------
    // 1. should_returnPost_when_idExists
    // ---------------------------------------------------------------
    @Test
    void should_returnPost_when_idExists() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.of(samplePost));

        // when
        Post result = postService.getPostById(1L);

        // then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getTitle()).isEqualTo("Tytuł testowy");
        verify(postRepository, times(1)).findById(1L);
    }

    // ---------------------------------------------------------------
    // 2. should_throwException_when_postNotFound
    // ---------------------------------------------------------------
    @Test
    void should_throwException_when_postNotFound() {
        // given
        when(postRepository.findById(99L)).thenReturn(Optional.empty());

        // when / then
        assertThatThrownBy(() -> postService.getPostById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("99");

        verify(postRepository, times(1)).findById(99L);
    }

    // ---------------------------------------------------------------
    // 3. should_returnPosts_when_searchByKeyword
    // ---------------------------------------------------------------
    @Test
    void should_returnPosts_when_searchByKeyword() {
        // given
        Post matchingPost = new Post(
                2L,
                List.of(),
                false,
                LocalDateTime.now(),
                "Anna Nowak",
                PostCategory.EVENT,
                "Treść z słowem kluczowym",
                "Szukany tytuł"
        );
        when(postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                "szukany", PostCategory.INFO.name()))
                .thenReturn(List.of(matchingPost));

        // when
        List<Post> results = postService.getPostsByTitleOrCategory("szukany", PostCategory.INFO);

        // then
        assertThat(results).hasSize(1);
        assertThat(results.get(0).getTitle()).isEqualTo("Szukany tytuł");
        verify(postRepository).findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(
                "szukany", PostCategory.INFO.name());
    }

    // ---------------------------------------------------------------
    // 4. should_publishPost_and_fireEvent
    // ---------------------------------------------------------------
    @Test
    void should_publishPost_and_fireEvent() {
        // given
        Post newPost = new Post(
                null,
                List.of(),
                false,
                LocalDateTime.now(),
                "Piotr Wiśniewski",
                PostCategory.URGENT,
                "Pilne ogłoszenie!",
                "PILNE"
        );
        Post savedPost = new Post(
                5L,
                List.of(),
                false,
                newPost.getCreatedAt(),
                "Piotr Wiśniewski",
                PostCategory.URGENT,
                "Pilne ogłoszenie!",
                "PILNE"
        );
        when(postRepository.save(newPost)).thenReturn(savedPost);

        // when
        Post result = postService.createPost(newPost);

        // then
        assertThat(result.getId()).isEqualTo(5L);

        // weryfikacja, że event został opublikowany z właściwym postem
        ArgumentCaptor<PostPublishedEvent> eventCaptor = ArgumentCaptor.forClass(PostPublishedEvent.class);
        verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
        assertThat(eventCaptor.getValue().getPost().getId()).isEqualTo(5L);
    }

    // ---------------------------------------------------------------
    // 5. should_deletePost_when_exists
    // ---------------------------------------------------------------
    @Test
    void should_deletePost_when_exists() {
        // given
        when(postRepository.findById(1L)).thenReturn(Optional.of(samplePost));

        // when
        Post deleted = postService.deletePost(1L);

        // then
        assertThat(deleted).isNotNull();
        assertThat(deleted.getId()).isEqualTo(1L);
        verify(postRepository, times(1)).deleteById(1L);
    }

    // ---------------------------------------------------------------
    // 6. (bonus) should_returnAllPosts
    // ---------------------------------------------------------------
    @Test
    void should_returnAllPosts() {
        // given
        Post post2 = new Post(2L, List.of(), true, LocalDateTime.now(),
                "Maria Zielińska", PostCategory.EVENT, "Oferta pracy", "Szukamy pracownika");
        when(postRepository.findAll()).thenReturn(List.of(samplePost, post2));

        // when
        List<Post> all = postService.getAllPosts();

        // then
        assertThat(all).hasSize(2);
        verify(postRepository).findAll();
    }

    // ---------------------------------------------------------------
    // 7. (bonus) should_returnPinnedPosts_only
    // ---------------------------------------------------------------
    @Test
    void should_returnPinnedPosts_only() {
        // given
        Post pinnedPost = new Post(3L, List.of(), true, LocalDateTime.now(),
                "Admin", PostCategory.INFO, "Ważne info na górze", "Przypięte");
        when(postRepository.findByPinnedIsTrue()).thenReturn(List.of(pinnedPost));

        // when
        List<Post> pinned = postService.getPinnedPosts();

        // then
        assertThat(pinned).hasSize(1);
        assertThat(pinned.get(0).getPinned()).isTrue();
        verify(postRepository).findByPinnedIsTrue();
    }
}
