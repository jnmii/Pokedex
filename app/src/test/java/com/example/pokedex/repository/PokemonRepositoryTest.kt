package com.example.pokedex.repository

import com.example.pokedex.data.remote.PokeApi
import com.example.pokedex.data.remote.responses.PokemonList
import com.example.pokedex.util.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PokemonRepositoryTest {

    // Mock the PokeApi interface
    @Mock
    lateinit var pokeApi: PokeApi

    // Create the PokemonRepository instance
    lateinit var pokemonRepository: PokemonRepository

    @Before
    fun setup() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this)

        // Create the PokemonRepository instance with the mock PokeApi
        pokemonRepository = PokemonRepository(pokeApi)
    }

    @Test
    fun testGetPokemonList_success() {
        // Mock the API response for getPokemonList
        val limit = 10
        val offset = 0
        val mockResponse = PokemonList(/* Provide your mock data here */)
        runBlocking {
            `when`(pokeApi.getPokemonList(limit, offset)).thenReturn(mockResponse)
        }

        // Call the getPokemonList function and verify the result
        val result = runBlocking {
            pokemonRepository.getPokemonList(limit, offset)
        }

        // Verify that the result is a success and contains the correct data
        assert(result is Resource.Success)
        assertEquals(mockResponse, (result as Resource.Success).data)
    }

    @Test
    fun testGetPokemonList_error() {
        // Mock an exception for getPokemonList
        val limit = 10
        val offset = 0
        runBlocking {
            `when`(pokeApi.getPokemonList(limit, offset)).thenThrow(Exception())
        }

        // Call the getPokemonList function and verify the result
        val result = runBlocking {
            pokemonRepository.getPokemonList(limit, offset)
        }

        // Verify that the result is an error
        assert(result is Resource.Error)
    }


}
