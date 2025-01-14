package kr.megaptera.smash.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GameTargetMemberCountTest {
    @Test
    void equals() {
        assertThat(new GameTargetMemberCount(10)).isEqualTo(new GameTargetMemberCount(10));
        assertThat(new GameTargetMemberCount(10)).isNotEqualTo(new GameTargetMemberCount(15));
        assertThat(new GameTargetMemberCount(10)).isNotEqualTo(null);
        assertThat(new GameTargetMemberCount(10)).isNotEqualTo(10);
    }
}
