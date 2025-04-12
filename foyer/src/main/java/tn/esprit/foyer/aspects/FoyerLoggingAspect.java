package tn.esprit.foyer.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class FoyerLoggingAspect {

    // Avant l'exécution de n'importe quelle méthode d'un service
    @Before("execution(* tn.esprit.foyer.services.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info(">> Entrée dans la méthode : {}", name);
    }

    // Après une exécution réussie de la méthode retrieveFoyer de FoyerServiceImpl
    @AfterReturning("execution(* tn.esprit.foyer.services.FoyerServiceImpl.retrieveFoyer(..))")
    public void logMethodExit1(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info(">> Sortie de la méthode sans erreur : {}", name);
    }

    // Si une exception est levée par n'importe quelle méthode de service
    @AfterThrowing("execution(* tn.esprit.foyer.services.*.*(..))")
    public void logMethodExitWithError(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.error(">> Sortie de la méthode avec erreur : {}", name);
    }

    // Après l'exécution de n'importe quelle méthode de service (succès ou exception)
    @After("execution(* tn.esprit.foyer.services.*.*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info(">> Fin de la méthode : {}", name);
    }
}
