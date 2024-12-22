// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.2" apply false
}


tasks.register("clean").configure {
    delete("build")
}
tasks.register("copyGitHooks", Copy::class.java) {
    description = "Copies the git hooks from /gita-hooks to the .git folder."
    group = "git hooks"
    from("$rootDir/scripts/pre-commit")
    into("$rootDir/.git/hooks/")
}
tasks.register("installGitHooks") {
    description = "Installs the pre-commit git hooks from /git-hooks."
    group = "git hooks"
    dependsOn("copyGitHooks")
    doLast {
        val isWindows = System.getProperty("os.name").toLowerCase().contains("win")
        val gitHooksDir = File(rootDir, ".git/hooks")
        val preCommitHook = File(gitHooksDir, "pre-commit")

        if (preCommitHook.exists()) {
            if (!isWindows) {
                // Unix-like 환경에서만 chmod 실행
                exec {
                    commandLine("chmod", "+x", preCommitHook.absolutePath)
                }
            }
            logger.info("Git hook installed successfully: ${preCommitHook.path}")
        } else {
            logger.error("Failed to install git hook. The pre-commit file does not exist.")
        }
    }
}
afterEvaluate {
    tasks.getByPath(":app:preBuild").dependsOn(":installGitHooks")
}