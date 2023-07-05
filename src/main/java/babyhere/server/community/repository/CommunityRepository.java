package babyhere.server.community.repository;

import babyhere.server.community.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long>{

    Community findById(long id);
    List<Community> findAllByOrderByHits();
    List<Community> findAllByOrderByDateDesc();

}
