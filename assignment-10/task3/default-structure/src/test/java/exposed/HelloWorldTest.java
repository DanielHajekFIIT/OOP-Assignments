package exposed;

import org.jikvict.tasks.exposed.HelloWorld;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HelloWorldTest {

    @Test
    @DisplayName("should return correct greeting for a name")
    void shouldReturnCorrectGreeting() {
        // Given
        String name = "World";

        // When
        String result = HelloWorld.sayHello(name);

        // Then
        assertThat(result).isEqualTo("Hello, World!");
    }

    @Test
    @DisplayName("should greet multiple names")
    void shouldGreetMultipleNames() {
        // Given
        String[] names = {"Alice", "Bob"};

        // When
        String result = HelloWorld.sayHelloMultiple(names);

        // Then
        assertThat(result).isEqualTo("Hello, Alice!\nHello, Bob!");
    }

    @Test
    @DisplayName("should count greetings")
    void shouldCountGreetings() {
        // Given
        String[] names = {"Alice", "Bob", "Charlie"};

        // When
        int result = HelloWorld.countGreetings(names);

        // Then
        assertThat(result).isEqualTo(3);
    }
}
