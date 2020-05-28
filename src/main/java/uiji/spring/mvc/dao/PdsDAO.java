package uiji.spring.mvc.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import uiji.spring.mvc.vo.BoardVO;
import uiji.spring.mvc.vo.PdsVO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository("pdao")
public class PdsDAO {

    // MemberDAO에서 사용할 dbcp2 객체를
    // 스프링에 의해 DI 받음
    private JdbcTemplate jdbcTemplate = null;

    // jdbc.properties에 정의된 JoinSQL들 가져오기
    @Value("#{jdbc['insertPdsSQL']}") private String insertPdsSQL;
    @Value("#{jdbc['selectPdsSQL']}")private String selectPdsSQL;
    @Value("#{jdbc['selectOnePdsSQL']}")private String selectOnePdsSQL;


    @Autowired
    public PdsDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 자료실 데이터를 pds테이블에 저장
    public boolean insertPds( PdsVO p ) {
        // sql 정의
//        String sql = "insert into Board (title,userid,contents) values (?,?,?)";

        // 매개변수 정의
        Object[] params = new Object[] {
             p.getTitle(), p.getUserid(), p.getContents(),
                p.getFname(),p.getFsize(),p.getFdown(),p.getFtype()
        };

        // 매개변수 타입 정의 - 생략 ^^;

        // 샐행
        boolean isInsert = false;
        if (jdbcTemplate.update(insertPdsSQL, params) > 0)
            isInsert = true;

        return isInsert;
    }

    // 게시판데이터 중에서 글번호/제목/작성자/작성일/추천/조회만
    // 골라서 동적배열에 담아 반환함
    // 스프링에서는 RowMapper라는 클래스를 이용해서
    // select문의 결과를 처리할 수 있음
    public List<PdsVO> selectPds() {

        RowMapper<PdsVO> mapper = new PdsRowMapper();


        return jdbcTemplate.query(selectPdsSQL, mapper);
    }

    // 글번호로 본문글 조회
    public PdsVO selectOnePds(String pno) {


        Object[] params = new Object[] { pno };

        RowMapper<PdsVO> mapper = new PdsOneMapper();

        PdsVO pvo = jdbcTemplate.queryForObject(selectOnePdsSQL, mapper, params);

        return pvo;
    }

    // selectBoard의 RowMapper 내부 클래스
    private class PdsRowMapper implements RowMapper<PdsVO> {

        @Override
        public PdsVO mapRow(ResultSet rs, int num) throws SQLException {

            PdsVO pvo = new PdsVO(
                 rs.getString("pno"),
                 rs.getString("title"),
                 rs.getString("userid"),
                 rs.getString("regdate"),
                 rs.getString("thumbup"),
                 rs.getString("views"),
                 null,null,null,null,null
            );

            return pvo;
        }
    }

    // selectOneBoard의 RowMapper 내부 클래스
    private class PdsOneMapper implements RowMapper<PdsVO> {
        @Override
        public PdsVO mapRow(ResultSet rs, int num) throws SQLException {

            PdsVO pvo = new PdsVO(
                    rs.getString("pno"),
                    rs.getString("title"),
                    rs.getString("userid"),
                    rs.getString("regdate"),
                    rs.getString("thumbup"),
                    rs.getString("views"),
                    rs.getString("contents"),
                    rs.getString("fname"),
                    rs.getString("fsize"),
                    rs.getString("fdown"),
                    rs.getString("ftype")
            );

            return pvo;
        }
    }
}