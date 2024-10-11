package dev.gallardo.miarmacore.config;

import java.util.Optional;

public record CommandWrapper(
        java.lang.String name,
        java.lang.String description,
        java.lang.String usage,
        PermissionWrapper permission,
        String[] messages,
        CommandWrapper[] subcommands
) {

    // Constructor completo
    public static CommandWrapper of(java.lang.String name,
                                    java.lang.String description,
                                    java.lang.String usage,
                                    PermissionWrapper permission,
                                    String[] messages,
                                    CommandWrapper[] subcommands) {
        return new CommandWrapper(name, description, usage, permission, messages, subcommands);
    }

    // Constructor sin subcomandos
    public static CommandWrapper of(java.lang.String name,
                                    java.lang.String description,
                                    java.lang.String usage,
                                    PermissionWrapper permission,
                                    String[] messages) {
        return new CommandWrapper(name, description, usage, permission, messages, null);
    }

    // Constructor sin mensajes ni subcomandos
    public static CommandWrapper of(java.lang.String name,
                                    java.lang.String description,
                                    java.lang.String usage,
                                    PermissionWrapper permission) {
        return new CommandWrapper(name, description, usage, permission, null, null);
    }

    // Constructor sin uso, sin mensajes, ni subcomandos
    public static CommandWrapper of(java.lang.String name,
                                    java.lang.String description,
                                    PermissionWrapper permission,
                                    String[] messages) {
        return new CommandWrapper(name, description, null, permission, messages, null);
    }

    // Opcional: Métodos helper para obtener valores opcionales
    public Optional<java.lang.String> usageOptional() {
        return Optional.ofNullable(usage);
    }

    public Optional<String[]> messagesOptional() {
        return Optional.ofNullable(messages);
    }

    public Optional<CommandWrapper[]> subcommandsOptional() {
        return Optional.ofNullable(subcommands);
    }
}

