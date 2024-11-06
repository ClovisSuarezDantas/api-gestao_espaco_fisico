package api.gef.repository;

import api.gef.entity.Usuario;
import api.gef.enums.UsuarioTipoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findUsuarioByEmail(String nome);
    Optional<List<Usuario>> findUsuariosByUsuarioTipo(UsuarioTipoEnum usuarioTipoEnum);
}
