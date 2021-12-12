package com.joaogsrocha._SpringREST.controller;

import com.google.gson.Gson;
import com.joaogsrocha._SpringREST.model.CustomPrincipal;
import com.joaogsrocha._SpringREST.model.roleuser.RoleUser;
import com.joaogsrocha._SpringREST.model.user.User;
import com.joaogsrocha._SpringREST.services.implementation.RoleUserServiceImpl;
import com.joaogsrocha._SpringREST.services.implementation.UserServiceImpl;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
@RequestMapping("api/user")
public class UserController {
    @Autowired
    private UserServiceImpl userRepository;
    @Autowired
    private RoleUserServiceImpl roleUserRepository;

    @Autowired
    private RestTemplate restTemplate;


    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    @RequestMapping(value = "health",
            method = RequestMethod.GET,
            produces =  {MimeTypeUtils.APPLICATION_JSON_VALUE},
            headers = "Accept=application/json")
    public ResponseEntity<String> health() {
        try {
                String response = "OK   " + now();
            return new ResponseEntity<String>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }
    }

   @GetMapping("/findall")
    public ResponseEntity<Iterable<User>> findAll() {

        try {
            CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            if(!principal.getUsername().equals("admin"))
                return new ResponseEntity<Iterable<User>>(
                        HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<Iterable<User>>(
                    userRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Iterable<User>>(
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{id}")
    @PreAuthorize("hasAuthority('role_user')")
    public ResponseEntity<User> find(@PathVariable("id") Long id) {
        try {
            CustomPrincipal principal = (CustomPrincipal) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            if(!principal.getId().equals(String.valueOf(id)))
                return new ResponseEntity<User>(
                        HttpStatus.UNAUTHORIZED);
            return new ResponseEntity<User>(
                    userRepository.find(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<User>(
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            //TODO: Make a check to see if UserPoints Service is alive or not. It not exception/cancel creation.
            // /userpoints/health is the endpoint to check
            User nuser = userRepository.create(user);
            RoleUser nroleUser = new RoleUser();
            nroleUser.setUserid(user.getId());
            nroleUser.setRoleid(Long.parseLong("2"));
            roleUserRepository.create(nroleUser);

            restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            JSONObject personJsonObject = new JSONObject();
            personJsonObject.put("userid", user.getId());

            HttpEntity<String> request =
                    new HttpEntity<String>(personJsonObject.toString(), headers);



            String personResultAsJsonStr =
                    restTemplate.postForObject("http://localhost:9002/api/userpoints/create/"+user.getId(), request, String.class );
            System.out.print(new Gson().toJson(nuser).toString());
            return new ResponseEntity<User>(nuser,
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<User>(
                    HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "update",
            method = RequestMethod.PUT,
            produces =  {MimeTypeUtils.APPLICATION_JSON_VALUE},
            consumes = { MimeTypeUtils.APPLICATION_JSON_VALUE},
            headers = "Accept=application/json")
    public ResponseEntity<Void> update(@RequestBody User user) {
        try {
            userRepository.update(user);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(
                    HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "delete/{id}",
            method = RequestMethod.DELETE
    )
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            userRepository.delete(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(
                    HttpStatus.BAD_REQUEST);
        }
    }
}
