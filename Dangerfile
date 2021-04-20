success_file_path = "./build/reports/success"
detekt_file_path = "./build/reports/detekt.xml"
lint_file_path = "./build/reports/android-lint.xml"

if File.file?(detekt_file_path)
    checkstyle_format.base_path = Dir.pwd
    checkstyle_format.report("./build/reports/detekt.xml")
end

if File.file?(lint_file_path)
    android_lint.report_file = "./build/reports/android-lint.xml"
    android_lint.skip_gradle_task = true
    android_lint.lint(inline_mode: true)
end

# Если на предыдущем этапе gradle сборка упала, то success файл будет отстуствовать
# В этом случае мы валим danger, MR нельзя мержить
unless File.file?(success_file_path)
    fail("Сборка не удалась. Проверьте замечания или ошибки сборки")
end