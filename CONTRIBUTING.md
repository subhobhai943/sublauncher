# Contributing to SubLauncher

Thank you for your interest in contributing to SubLauncher! This document provides guidelines and instructions for contributing.

## Code of Conduct

By participating in this project, you agree to:
- Be respectful and inclusive
- Provide constructive feedback
- Focus on what is best for the community
- Show empathy towards other community members

## How Can I Contribute?

### Reporting Bugs

Before creating bug reports, please check existing issues to avoid duplicates.

**When submitting a bug report, include:**
- Clear, descriptive title
- Steps to reproduce the issue
- Expected behavior vs actual behavior
- Screenshots (if applicable)
- Your device information:
  - Android version
  - Device model
  - SubLauncher version
- Crash logs (if available)

### Suggesting Enhancements

**For feature requests, provide:**
- Clear description of the feature
- Use cases and examples
- Why this would be useful to users
- Possible implementation approach (optional)

### Pull Requests

1. **Fork the Repository**
   ```bash
   # Click 'Fork' on GitHub, then clone your fork
   git clone https://github.com/YOUR-USERNAME/sublauncher.git
   cd sublauncher
   ```

2. **Create a Feature Branch**
   ```bash
   git checkout -b feature/your-feature-name
   # or
   git checkout -b bugfix/issue-description
   ```

3. **Make Your Changes**
   - Follow the coding standards (see below)
   - Write clear commit messages
   - Test your changes thoroughly

4. **Commit Your Changes**
   ```bash
   git add .
   git commit -m "Add feature: description of feature"
   ```

5. **Push to Your Fork**
   ```bash
   git push origin feature/your-feature-name
   ```

6. **Open a Pull Request**
   - Go to the original repository
   - Click "New Pull Request"
   - Select your branch
   - Fill out the PR template
   - Link any related issues

## Development Guidelines

### Coding Standards

**Java Style:**
- Use 4 spaces for indentation (no tabs)
- Opening braces on same line
- Clear, descriptive variable names
- Add comments for complex logic
- Follow existing code patterns

**Example:**
```java
public class Example {
    private String exampleField;
    
    public void exampleMethod() {
        if (condition) {
            // Do something
        }
    }
}
```

### Commit Messages

Follow conventional commit format:

```
type: subject

body (optional)

footer (optional)
```

**Types:**
- `feat`: New feature
- `fix`: Bug fix
- `docs`: Documentation changes
- `style`: Code formatting (no logic change)
- `refactor`: Code refactoring
- `test`: Adding tests
- `chore`: Maintenance tasks

**Examples:**
```
feat: add profile selection screen

fix: resolve crash on Android 11 devices

docs: update README with new installation steps
```

### Testing

Before submitting:

1. **Build successfully**
   ```bash
   ./gradlew :app_sublauncher:assembleDebug
   ```

2. **Test on real device**
   - Install and run the APK
   - Test your specific changes
   - Check for crashes or errors

3. **Test different scenarios**
   - Different Android versions (if possible)
   - Different screen sizes
   - Edge cases

### File Structure

When adding new files, follow the existing structure:

```
app_sublauncher/src/main/java/com/subhobhai/sublauncher/
├── activities/      # Activity classes
├── fragments/       # Fragment classes
├── models/          # Data models
├── utils/           # Utility classes
├── adapters/        # RecyclerView adapters
└── services/        # Background services
```

## What to Contribute

### Good First Issues

Look for issues labeled `good first issue` or `help wanted`:
- UI improvements
- Bug fixes
- Documentation updates
- Translation improvements

### Priority Areas

1. **Core Functionality**
   - Minecraft version downloading
   - Profile management
   - Settings implementation

2. **User Experience**
   - UI/UX improvements
   - Error handling
   - Loading states

3. **Documentation**
   - Code comments
   - User guides
   - API documentation

4. **Testing**
   - Bug fixes
   - Edge case handling
   - Performance optimization

## Review Process

1. **Automated Checks**
   - Build must succeed
   - No merge conflicts

2. **Code Review**
   - Maintainer will review your code
   - May request changes
   - Discussion in PR comments

3. **Approval & Merge**
   - Once approved, PR will be merged
   - Your contribution will be in the next release!

## Recognition

Contributors will be:
- Listed in release notes
- Credited in the README
- Added to the contributors list

## Getting Help

**Need help contributing?**
- Comment on the issue you want to work on
- Ask questions in discussions
- Check existing documentation
- Review PojavLauncher code for examples

## Legal

By contributing, you agree that:
- Your contributions will be licensed under LGPL v3
- You have the right to submit the work
- You understand the open-source nature of the project

## Resources

- [Android Development Guide](https://developer.android.com/guide)
- [Git Tutorial](https://git-scm.com/book/en/v2)
- [Markdown Guide](https://www.markdownguide.org/)
- [PojavLauncher Wiki](https://pojavlauncherteam.github.io/)

## Questions?

Feel free to:
- Open a discussion
- Comment on issues
- Reach out to maintainers

Thank you for contributing to SubLauncher! 🚀
