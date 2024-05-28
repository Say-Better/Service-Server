module.exports = {
    extends: ['gitmoji'],
    rules: {
        /**
         * [Header] ex) Feat: add new feature
         * 1. 헤더는 대문자로 시작
         * 2. 헤더의 길이는 100자로 제한
         * 3. 다음의 타입만 가능 (feat, fix, docs, style, refactor, test, chore)
         * 4. 타입은 대문자로 시작
         */
        "header-max-length": [2, "always", 100],
        "type-enum": [
            2,
            "always",
            ["Feat", "Fix", "Docs", "Style", "Refactor", "Test", "Chore"],
        ],
        "type-case": [2, "always", "sentence-case"],

        /**
         * [Body]
         * 1. 본문은 비어있어도 됨
         * 2. 본문은 빈 줄 없이 시작
         * 3. 본문은 한 줄에 100자로 제한
         */
        "body-empty": [1, "never"],
        "body-leading-blank": [0, "never"],
        "body-max-line-length": [2, "always", 100],

        /**
         * [Footer]
         * 1. 푸터는 비어있어도 됨
         * 2. 푸터는 빈 줄 없이 시작
         * 3. 푸터는 한 줄에 100자로 제한
         */
        "footer-empty": [1, "never"],
        "footer-leading-blank": [0, "never"],
        "footer-max-line-length": [2, "always", 100],

        /**
         * [Ignores]
         * - scope
         */
        "scope-empty": [2, "always"],
    }
}
